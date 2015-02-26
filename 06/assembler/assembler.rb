class Parser
end

class CodeGenerator
end

asm_lines = File.readlines(ARGV[0])
hack_lines = asm_lines
fname, _fext = ARGV[0].split('.')

File.open("#{fname}.hack", 'w') do |f|
  hack_lines.each do |line|
    f.puts(line)
  end
end
