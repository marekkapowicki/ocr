#!/usr/bin/env python3
import argparse
import warnings
import numpy as np
from skimage import io, img_as_ubyte
from skimage.exposure import is_low_contrast
from skimage.io import imsave


def enhanceContrastIfRequired(imagePath, outputPath):
    warnings.filterwarnings("ignore")
    imagePath = "" + imagePath
    color = io.imread(imagePath)
    lowContrast = is_low_contrast(color)
    print(lowContrast)
    if(lowContrast):
        print('low contrast detected. enhancing image')
        p2, p98 = np.percentile(img, (2, 98))
        img_rescale = exposure.rescale_intensity(color, in_range=(p2, p98))

    else:
        print('contrast enhancing not needed')

    imsave('' + outputPath, color)
    color

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='applies adaptative binarization and saves output.')
    parser.add_argument('-i', '--input_path', dest="input_path", type=str, required=True, help="image path")
    parser.add_argument('-o', '--output_path', dest="output_path", type=str, required=True, help="output path")
    args = parser.parse_args()
# if not os.path.exists(args.input_path):
#     raise IOError('input file does not exit')
output = enhanceContrastIfRequired(args.input_path, args.output_path)
