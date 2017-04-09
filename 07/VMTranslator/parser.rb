module VmTranslator
  autoload :Command, './command'

  class Parser
    attr_reader :commands

    def initialize(file_path)
      @vm_file = File.new(file_path)
      @commands = []
      parse
    end

    def parse
      @vm_file.each do |line|
        @commands << Command.create(line) unless skip? line
      end
    end

    def skip?(line)
      line.start_with?('//') || line =~ /^\s*$/
    end
  end
end
