require_relative 'pattern.rb'

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


class ProgramParser < Parser
  attr_reader :comp, :dest, :jump, :command, :symbol

  def initialize(filename, symbol_map)
    super
    @next_addr = 16 # RAM address
  end

  private

  def skip?(line)
    super || line =~ Pattern.label
  end

  def parse_line(line)
    at_match = line.match(Pattern.at)
    dest_match = line.match(Pattern.dest)
    jump_match = line.match(Pattern.jump)
    if at_match
      @command = :a
      @symbol = at_match[1].strip
    elsif dest_match
      @command = :c
      @dest = dest_match[1].strip
      @comp = dest_match[2].strip
      @jump = nil
    elsif jump_match
      @command = :c
      @dest = nil
      @comp = jump_match[1].strip
      @jump = jump_match[2].strip
    end
  end
end


class LabelParser < Parser
  def initialize(filename, symbol_map)
    super
    @next_addr = 0 # ROM address
  end

  private

  def parse_line(line)
    label_match = line.match(Pattern.label)
    if label_match
      symbol_map[label_match[1]] = @next_addr
    else
      @next_addr += 1
    end
  end
end
