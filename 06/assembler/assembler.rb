require_relative 'code.rb'
require_relative 'parser.rb'

# monkey-patch Integer to convert numbers to binary
class Integer
  def to_bin(width=16)
    '%0*b' % [width, self]
  end
end

symbol_map = {
  'SP'     => 0,
  'LCL'    => 1,
  'ARG'    => 2,
  'THIS'   => 3,
  'THAT'   => 4,
  'R0'     => 0,
  'R1'     => 1,
  'R2'     => 2,
  'R3'     => 3,
  'R4'     => 4,
  'R5'     => 5,
  'R6'     => 6,
  'R7'     => 7,
  'R8'     => 8,
  'R9'     => 9,
  'R10'    => 10,
  'R11'    => 11,
  'R12'    => 12,
  'R13'    => 13,
  'R14'    => 14,
  'R15'    => 15,
  'SCREEN' => 16384,
  'KBD'    => 24576
}

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
