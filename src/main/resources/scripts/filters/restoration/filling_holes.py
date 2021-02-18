#!/usr/bin/env python3
import argparse
import warnings
import numpy as np
from skimage.morphology import square
from skimage.morphology import opening
from skimage.color import rgb2gray
from skimage import io, img_as_ubyte
from skimage.io import imsave


def reconstruction(imagePath, outputPath):
    warnings.filterwarnings("ignore")
    imagePath = "" + imagePath
    image = io.imread(imagePath)
    img = rgb2gray(image)
    image = img_as_ubyte(img)
    # seed = np.copy(image)
    # seed[1:-1, 1:-1] = image.max()
    # mask = image
    filled = opening(image, square(3))
    imsave('' + outputPath, filled)
    filled


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='applies adaptative binarization and saves output.')
    parser.add_argument('-i', '--input_path', dest="input_path", type=str, required=True, help="image path")
    parser.add_argument('-o', '--output_path', dest="output_path", type=str, required=True, help="output path")
    args = parser.parse_args()
# if not os.path.exists(args.input_path):
#     raise IOError('input file does not exit')
output = reconstruction(args.input_path, args.output_path)
