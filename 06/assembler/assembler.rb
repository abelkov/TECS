require_relative 'code.rb'
require_relative 'parser.rb'

# monkey-patch Integer to convert numbers to binary
class Integer
  def to_bin(width=16)
    '%0*b' % [width, self]
  end
end

parser = Parser.new(ARGV[0])
filename, _file_extension = ARGV[0].split('.')

File.open("#{ filename }.hack", 'w') do |f|
  parser.parse do |p|
    if p.command == :a
      f.puts p.symbol.to_i.to_bin
    else
      c = Code.comp(p.comp)
      d = Code.dest(p.dest)
      j = Code.jump(p.jump)
      f.puts "111#{ c }#{ d }#{ j }"
    end
  end
end
