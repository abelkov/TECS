class Parser
  attr_reader :comp, :dest, :jump, :command, :symbol

  def initialize(filename)
    @filename = filename
  end

  def parse(&block)
    File.readlines(@filename).each do |line|
      next if skip?(line)
      parse_line(line)
      yield(self)
    end
  end

  private

  def skip?(line)
    line.start_with?('//') || line =~ /^\s*$/
  end

  def parse_line(line)
    at_match = line.match('\s*@(.*)\s*')
    if at_match
      @command = :a
      @symbol = at_match[1]
    else
      @command = :c
      @symbol = nil
    end
  end
end
