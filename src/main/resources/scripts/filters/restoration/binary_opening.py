#!/usr/bin/env python3
import argparse
import warnings
from skimage import io, img_as_ubyte
import numpy as np
from skimage.color import rgb2gray
from skimage.morphology import opening
from skimage.io import imsave


def open(imagePath, outputPath):
    warnings.filterwarnings("ignore")
    imagePath = "" + imagePath
    color = io.imread(imagePath)
    img = rgb2gray(color)
    image = img_as_ubyte(img)
    opened = opening(image)
    imsave('' + outputPath, opened)
    opened


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='applies adaptative binarization and saves output.')
    parser.add_argument('-i', '--input_path', dest="input_path", type=str, required=True, help="image path")
    parser.add_argument('-o', '--output_path', dest="output_path", type=str, required=True, help="output path")
    args = parser.parse_args()
# if not os.path.exists(args.input_path):
#     raise IOError('input file does not exit')
output = open(args.input_path, args.output_path)
