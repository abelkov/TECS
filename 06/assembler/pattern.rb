module Pattern
  def self.at
    /^\s*@(.*)\s*$/
  end

  def self.dest
   /^\s*(.*)=(.*)\s*$/
  end

  def self.jump
    /^\s*(.*);(.*)\s*$/
  end

  def self.label
    /^\s*\((.*)\)\s*$/
  end

  def self.empty
    /^\s*$/
  end
end
