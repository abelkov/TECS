require_relative 'pattern.rb'
require_relative 'parser.rb'

class LabelParser < Parser
  def initialize(filename, symbol_map)
    super
    @next_addr = 0 # ROM address
  end

  private

  def parse_line(line)
    label_match = line.match(Pattern.label)
    if label_match
      @symbol_map[label_match[1]] = @next_addr
    else
      @next_addr += 1
    end
  end
end
