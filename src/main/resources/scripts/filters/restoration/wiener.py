#!/usr/bin/env python3
import argparse
import warnings
import numpy as np
from skimage.color import rgb2gray
from skimage import io, img_as_ubyte
from skimage import color, data, restoration
from skimage.io import imsave
from scipy.signal import convolve2d as conv2


def wiener(imagePath, outputPath):
    warnings.filterwarnings("ignore")
    imagePath = "" + imagePath
    color = io.imread(imagePath)
    image = rgb2gray(color)
#     image = img_as_ubyte(img)
    psf = np.ones((5, 5)) / 25
    image = conv2(image, psf, 'same')
    image += 0.1 * image.std() * np.random.standard_normal(image.shape)
    cleaned, _ =  restoration.unsupervised_wiener(image, psf, clip=True)
    cleaned_uint8 = img_as_ubyte(cleaned)
    imsave('' + outputPath, cleaned_uint8)
    cleaned_uint8

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='applies adaptative binarization and saves output.')
    parser.add_argument('-i', '--input_path', dest="input_path", type=str, required=True, help="image path")
    parser.add_argument('-o', '--output_path', dest="output_path", type=str, required=True, help="output path")
    args = parser.parse_args()
# if not os.path.exists(args.input_path):
#     raise IOError('input file does not exit')
output = wiener(args.input_path, args.output_path)
