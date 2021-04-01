#!/usr/bin/env python3
import argparse
import warnings
from skimage import io, img_as_ubyte
from skimage.morphology import remove_small_objects, remove_small_holes
from skimage.io import imsave

# the filter apply after threshold
def cleaningObjects(imagePath, outputPath):
    warnings.filterwarnings("ignore")
    imagePath = "" + imagePath
    threshold = io.imread(imagePath)
    image = img_as_ubyte(threshold)
    cleaned = remove_small_holes(image, area_threshold=26)
    cleaned = remove_small_objects(cleaned, min_size=8)
    imsave('' + outputPath, cleaned)
    cleaned


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='apply filter')
    parser.add_argument('-i', '--input_path', dest="input_path", type=str, required=True, help="image path")
    parser.add_argument('-o', '--output_path', dest="output_path", type=str, required=True, help="output path")
    args = parser.parse_args()
# if not os.path.exists(args.input_path):
#     raise IOError('input file does not exit')
output = cleaningObjects(args.input_path, args.output_path)
