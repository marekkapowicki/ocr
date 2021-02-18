#!/usr/bin/env python3
import argparse
import warnings
from skimage import io
from skimage.filters import laplace
from skimage.io import imsave


def yen_threshold(imagePath, outputPath):
    warnings.filterwarnings("ignore")
    imagePath = "" + imagePath
    color = io.imread(imagePath)
    sharp = laplace(color)
    imsave('' + outputPath, sharp)
    sharp


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='applies adaptative binarization and saves output.')
    parser.add_argument('-i', '--input_path', dest="input_path", type=str, required=True, help="image path")
    parser.add_argument('-o', '--output_path', dest="output_path", type=str, required=True, help="output path")
    args = parser.parse_args()
# if not os.path.exists(args.input_path):
#     raise IOError('input file does not exit')
output = yen_threshold(args.input_path, args.output_path)
