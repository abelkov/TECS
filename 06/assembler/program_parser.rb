require_relative 'pattern.rb'
require_relative 'parser.rb'

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
    number_match = line.match(Pattern.at_number)
    symbol_match = line.match(Pattern.at_symbol)

    if number_match
      addr = number_match[1]
    elsif symbol_match
      addr = @symbol_map[symbol_match[1]]
      if addr.nil?
        addr = @symbol_map[symbol_match[1]] = @next_addr
        @next_addr += 1
      end
    end

    @command = :a
    @address = addr.to_i
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
