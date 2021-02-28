#!/usr/bin/env python3
import argparse
import warnings
import numpy as np
from skimage import io, img_as_ubyte
from skimage.filters import unsharp_mask
from skimage.io import imsave


def sharp(imagePath, outputPath):
    warnings.filterwarnings("ignore")
    imagePath = "" + imagePath
    color = io.imread(imagePath)
    sharp = unsharp_mask(color, radius=5, amount=1)
    sharp_uint8 = img_as_ubyte(sharp)
    imsave('' + outputPath, sharp_uint8)
    sharp_uint8

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='applies adaptative binarization and saves output.')
    parser.add_argument('-i', '--input_path', dest="input_path", type=str, required=True, help="image path")
    parser.add_argument('-o', '--output_path', dest="output_path", type=str, required=True, help="output path")
    args = parser.parse_args()
# if not os.path.exists(args.input_path):
#     raise IOError('input file does not exit')
output = sharp(args.input_path, args.output_path)
