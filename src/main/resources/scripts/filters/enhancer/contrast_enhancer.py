#!/usr/bin/env python3
import argparse
import warnings
import numpy as np
from skimage import io, img_as_ubyte
from skimage.exposure import is_low_contrast, rescale_intensity
from skimage.filters.rank import enhance_contrast
from skimage.io import imsave
from skimage.morphology import disk, ball


def enhanceContrastIfRequired(imagePath, outputPath):
    warnings.filterwarnings("ignore")
    imagePath = "" + imagePath
    color = io.imread(imagePath)
    lowContrast = is_low_contrast(color, fraction_threshold=0.25)
    if(lowContrast):
        print('low contrast detected. enhancing image')
        image_to_enhance = img_as_ubyte(color)
        enhanced = enhance_contrast(image_to_enhance, determineSelem(image_to_enhance))
        p2, p98 = np.percentile(enhanced, (2, 98))
        output = rescale_intensity(enhanced, in_range=(p2, p98))

    else:
        print('contrast enhancing not needed')
        output = color

    imsave('' + outputPath, output)
    output

def determineSelem(img):
    if(img.ndim == 3):
        return ball(5)
    else:
        return disk(5)
if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='applies adaptative binarization and saves output.')
    parser.add_argument('-i', '--input_path', dest="input_path", type=str, required=True, help="image path")
    parser.add_argument('-o', '--output_path', dest="output_path", type=str, required=True, help="output path")
    args = parser.parse_args()
# if not os.path.exists(args.input_path):
#     raise IOError('input file does not exit')
output = enhanceContrastIfRequired(args.input_path, args.output_path)
