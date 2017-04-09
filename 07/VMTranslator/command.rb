module VmTranslator
  module Command
    def self.create(line)
      if line.match?(/^push/)
        Push.new(line)
        # elsif line.match?(/^pop/)
        #   Pop.new(line)
      elsif line.match?(/^add/)
        Add.new(line)
      else
        raise 'unknown command'
      end
    end

    def initialize(line)
      @commented_line = "\n// " + line
      parse_line(line)
    end

    def asm_string
      @commented_line + asm
    end

    private

    def parse_line(line) end
  end

  class Push
    include Command

    def parse_line(line)
      parts = line.split(/\s/)
      @segment = parts[1]
      @index = parts[2]
    end

    def asm
      <<~ASM
        @#{@index}
        D=A
        @SP
        A=M
        M=D
        @SP
        M=M+1
      ASM
    end
  end

  class Add
    include Command

    def asm
      <<~ASM
        @SP
        A=M
        A=A-1
        D=M
        A=A-1
        M=D+M
        D=A+1
        @SP
        M=D
      ASM
    end
  end
end
