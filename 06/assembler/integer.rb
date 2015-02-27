# monkey-patch Integer to convert numbers to binary
class Integer
  def to_bin(width=16)
    '%0*b' % [width, self]
  end
end
