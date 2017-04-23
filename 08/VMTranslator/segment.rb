module VmTranslator

  module Segment
    @@name_map = {
      'local' => 'LCL',
      'argument' => 'ARG',
      'this' => 'THIS',
      'that' => 'THAT',
      'pointer' => 'pointer',
      'temp' => 'temp'

    }

    def self.create(name, index)
      if %w[local argument this that].include?(name)
        DirectSegment.new(name, index)
      elsif %w[pointer temp].include?(name)
        FixedSegment.new(name, index)
      elsif name == 'constant'
        ConstantSegment.new(name, index)
      elsif name == 'static'
        StaticSegment.new(name, index)
      else
        raise 'unknown segment: ' + name
      end
    end

    def initialize(name, index)
      @name = @@name_map[name]
      @index = index
    end

    def get_value_asm
      raise 'need to override'
    end

  end


  class DirectSegment
    include Segment

    def get_value_asm
      <<~ASM
        @#{@name}
        D=M
        @#{@index}
        A=D+A
        D=M
      ASM
    end

    def get_cell_address_asm
      <<~ASM
        @#{@name}
        D=M
        @#{@index}
        D=D+A
      ASM
    end

  end


  class FixedSegment
    include Segment

    @@map = {
      'pointer' => 3,
      'temp' => 5
    }

    def get_value_asm
      base = @@map[@name]
      <<~ASM
        @#{base}
        D=A
        @#{@index}
        A=D+A
        D=M
      ASM
    end

    def get_cell_address_asm
      base = @@map[@name]
      <<~ASM
        @#{base}
        D=A
        @#{@index}
        D=D+A
      ASM
    end

  end


  class ConstantSegment
    include Segment

    def get_value_asm
      <<~ASM
        @#{@index}
        D=A
      ASM
    end

    def get_cell_address_asm
      raise 'unsupported operation'
    end

  end


  class StaticSegment
    include Segment
    # very hacky way to get the VM program name
    # TODO: refactoring
    @@program_name = ARGV[0].split('.')[1]

    def get_value_asm
      <<~ASM
        @#{@@program_name}.#{@index}
        D=M
      ASM
    end

    def get_cell_address_asm
      <<~ASM
        @#{@@program_name}.#{@index}
        D=A
      ASM
    end

  end

end
