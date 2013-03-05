#!/usr/bin/env ruby -wKU

# Test if rmagick is working properly or not.
# When run, this file creates a image file 'path.gif' in the tmp directory.


require 'rubygems'

require 'RMagick'
include Magick
canvas = Magick::Image.new(240, 300,
              Magick::HatchFill.new('white','lightcyan2'))
gc = Magick::Draw.new

gc.fill('green')
gc.stroke('blue')
gc.stroke_width(2)
gc.path('M120,150 h-75 a75,75 0 1, 0 75,-75 z')
gc.fill('yellow')
gc.path('M108.5,138.5 v-75 a75,75 0 0,0 -75,75 z')
gc.draw(canvas)

canvas.write('pieslice.gif')



