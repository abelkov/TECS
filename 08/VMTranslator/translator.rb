module VmTranslator
  autoload :Parser, './parser'

  def self.translate
    vm_file_path = ARGV[0]
    dirname = File.dirname(vm_file_path)
    filename = File.basename(vm_file_path).split('.')[0]
    asm_file_path = File.join(dirname, filename) + '.asm'
    asm_file = File.new(asm_file_path, 'w')

    parser = Parser.new(vm_file_path)
    commands = parser.commands

    commands.each do |command|
      asm_file.puts(command.asm_string)
    end
  end
end

VmTranslator.translate
