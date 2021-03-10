#!/usr/bin/env python3
from skimage.io import imsave, imread
import argparse

def saveImage(imagePath, outputPath):
  imagePath = "" + imagePath
  color = imread(imagePath)
  imsave('' + outputPath, color)
  color

if __name__ == '__main__':
  parser = argparse.ArgumentParser(description='saving.')
  parser.add_argument('-i', '--input_path', dest="input_path", type=str, required=True, help="image path")
  parser.add_argument('-o', '--output_path', dest="output_path", type=str, required=True, help="output path")
  args = parser.parse_args()
# if not os.path.exists(args.input_path):
#     raise IOError('input file does not exit')
output = saveImage(args.input_path, args.output_path)


