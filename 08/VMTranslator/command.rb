module VmTranslator
  autoload :Segment, './segment'

  module Command
    def self.create(line)
      if line.match?(/^push/)
        Push.new(line)
      elsif line.match?(/^pop/)
        Pop.new(line)
      elsif line.match?(/^add/)
        Add.new(line)
      elsif line.match?(/^sub/)
        Sub.new(line)
      elsif line.match?(/^neg/)
        Neg.new(line)
      elsif line.match?(/^eq/)
        Eq.new(line)
      elsif line.match?(/^gt/)
        Gt.new(line)
      elsif line.match?(/^lt/)
        Lt.new(line)
      elsif line.match?(/^and/)
        And.new(line)
      elsif line.match?(/^or/)
        Or.new(line)
      elsif line.match?(/^not/)
        Not.new(line)
      else
        raise 'unknown command'
      end
    end

    def initialize(line)
      @commented_line = "\n///// " + line
      parse_line(line)
    end

    def asm_string
      @commented_line + asm
    end

    private

    def parse_line(line)
    end

  end

  class Push
    include Command

    def parse_line(line)
      parts = line.split(/\s/)
      @index = parts[2]
      @segment = Segment.create(parts[1], @index)
    end

    def asm
      <<~ASM
        #{@segment.get_value_asm}
        // push value to stack
        @SP
        M=M+1
        A=M-1
        M=D
      ASM
    end
  end

  class Pop
    include Command
    @@count = 0

    def parse_line(line)
      parts = line.split(/\s/)
      @index = parts[2]
      @segment = Segment.create(parts[1], @index)
    end

    def asm
      @@count += 1
      <<~ASM
        // save stack value and decrement SP
        @SP
        AM=M-1
        D=M
        @push-value-#{@@count}
        M=D
        // save segment cell address
        #{@segment.get_cell_address_asm}
        @push-cell-address-#{@@count}
        M=D
        // pop stack value to segment
        @push-value-#{@@count}
        D=M
        @push-cell-address-#{@@count}
        A=M
        M=D
      ASM
    end

  end

  class Branching
    include Command
    @@count = 0

    def asm
      @@count += 1

      true_label = "#{op}-#{@@count}"
      end_label = "#{op}-#{@@count}-END"
      <<~ASM
        @SP
        M=M-1
        A=M
        D=M
        A=A-1
        D=M-D
        @#{true_label}
        D;J#{op}
        @SP
        A=M-1
        M=0
        @#{end_label}
        0;JMP
        (#{true_label})
        @SP
        A=M-1
        M=-1
        (#{end_label})
      ASM
    end
  end

  class Eq < Branching
    def op
      'EQ'
    end
  end

  class Gt < Branching
    def op
      'GT'
    end
  end

  class Lt < Branching
    def op
      'LT'
    end
  end

  class Binary
    include Command

    def asm
      <<~ASM
        @SP
        A=M
        A=A-1
        D=M
        A=A-1
        M=M#{op}D
        D=A+1
        @SP
        M=D
      ASM
    end

    def op
      raise 'need to override'
    end
  end

  class Add < Binary
    def op
      '+'
    end
  end

  class Sub < Binary
    def op
      '-'
    end
  end

  class And < Binary
    def op
      '&'
    end
  end

  class Or < Binary
    def op
      '|'
    end
  end

  class Unary
    include Command

    def asm
      <<~ASM
        @SP
        A=M-1
        M=#{op}M
      ASM
    end

    def op
      raise 'need to override'
    end
  end

  class Neg < Unary
    def op
      '-'
    end
  end

  class Not < Unary
    def op
      '!'
    end
  end

end
