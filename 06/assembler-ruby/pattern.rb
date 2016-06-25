module Pattern
  def self.at
    /\s*@(.+)\s*/
  end

  def self.at_number
    /\s*@(\d+)\s*/
  end

  def self.at_symbol
    /\s*@([a-zA-Z_.$:]+[0-9a-zA-Z_.$:]*)\s*/
  end

  def self.dest
   /\s*([D|M|A]*)=([D|M|A|\-|+|&|\||!|0|1]*).*/
  end

  def self.jump
    /\s*(.*);([a-zA-Z]*).*/
  end

  def self.label
    /^\s*\((.*)\)\s*/
  end

  def self.empty
    /^\s*$/
  end
end
