require_relative 'pattern.rb'

# Abstract
class Parser
  def initialize(filename, symbol_map)
    @filename = filename
    @symbol_map = symbol_map
  end

  def parse(&block)
    File.readlines(@filename).each do |line|
      next if skip?(line)
      parse_line(line)
      yield(self) if block_given?
    end
  end

  private

  def skip?(line)
    line.start_with?('//') || line =~ Pattern.empty
  end

  def parse_line(line)
  end
end
