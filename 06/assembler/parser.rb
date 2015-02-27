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
  attr_reader :comp, :dest, :jump, :command, :address

  def initialize(filename, symbol_map)
    super
    @next_addr = 16 # RAM address
  end

  private

  def skip?(line)
    super || line =~ Pattern.label
  end

  def parse_line(line)
    case line
    when Pattern.at then parse_at(line)
    when Pattern.dest then parse_dest(line)
    when Pattern.jump then parse_jump(line)
    end
  end

  def parse_at(line)
    address = line.match(Pattern.at)[1]
    @command = :a
    @address = address.strip.to_i
  end

  def parse_dest(line)
    dest_match = line.match(Pattern.dest)
    @command = :c
    @dest = dest_match[1].strip
    @comp = dest_match[2].strip
    @jump = nil
  end

  def parse_jump(line)
    jump_match = line.match(Pattern.jump)
    @command = :c
    @dest = nil
    @comp = jump_match[1].strip
    @jump = jump_match[2].strip
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
