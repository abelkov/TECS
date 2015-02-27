require_relative 'code.rb'
require_relative 'integer.rb'
require_relative 'symbol_map.rb'
require_relative 'label_parser.rb'
require_relative 'program_parser.rb'

symbol_map = SymbolMap.symbols

# 1st pass: update the symbol map with labels
LabelParser.new(ARGV[0], symbol_map).parse

# 2nd pass: turn assembly into machine code
parser = ProgramParser.new(ARGV[0], symbol_map)
filename, _file_extension = ARGV[0].split('.')
File.open("#{ filename }.hack", 'w') do |f|
  parser.parse do |p|
    if p.command == :a
      f.puts(p.address.to_bin)
    else
      c = Code.comp(p.comp)
      d = Code.dest(p.dest)
      j = Code.jump(p.jump)
      f.puts("111#{ c }#{ d }#{ j }")
    end
  end
end
