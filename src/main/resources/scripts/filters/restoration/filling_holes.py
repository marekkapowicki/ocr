#!/usr/bin/env python3
import argparse
import warnings
from PIL import Image, ImageStat
import numpy as np
from skimage.color import gray2rgb, rgb2hsv, hsv2rgb
from skimage.segmentation import flood
from skimage.morphology import binary_opening, binary_closing, disk
from skimage import io, img_as_ubyte
from skimage.io import imsave

def isNotHsv(imagePath):
    pim = Image.open(imagePath)
    stat = ImageStat.Stat(pim)

    if len(stat.sum) != 4:
        return True
    else:
        return False
def isGrayscale(imagePath):
    pim = Image.open(imagePath).convert("RGB")
    stat = ImageStat.Stat(pim)

    if sum(stat.sum)/3 == stat.sum[0]:
        return True
    else:
        return False
def reconstruction(imagePath, outputPath):
  image = io.imread(imagePath)

  pim = Image.open(imagePath).convert("RGB")
  stat = ImageStat.Stat(pim)
  if (isGrayscale(imagePath)):
    image = gray2rgb(image)
  elif (isNotHsv(imagePath) ):
    image = rgb2hsv(image)
  pim = Image.open(imagePath)
  stat = ImageStat.Stat(pim)
  if len(stat.sum) != 4:
    image = rgb2hsv(image)

  img_hsv = image
  img_hsv_copy = np.copy(img_hsv)


  imgSize = (image.shape)
  w = min(imgSize[0], 200)
  h = min(imgSize[1], 160)
  # flood function returns a mask of flooded pixels
  mask = flood(img_hsv[..., 0], (w-1, h-1), tolerance=0.016)
  # Set pixels of mask to new value for hue channel
  img_hsv[mask, 0] = 0.5
  # Post-processing in order to improve the result
  # Remove white pixels from flag, using saturation channel
  mask_postprocessed = np.logical_and(mask,
                                      img_hsv_copy[..., 1] > 0.4)
  # Remove thin structures with binary opening
  mask_postprocessed = binary_opening(mask_postprocessed,
                                                 np.ones((3, 3)))
  # Fill small holes with binary closing
  mask_postprocessed = binary_closing(mask_postprocessed, disk(20))
  img_hsv_copy[mask_postprocessed, 0] = 0.5
  # img_hsv_copy_uint8 = img_as_ubyte(img_hsv_copy)
  #is_hsv
  if(len(img_hsv_copy.shape) == 3 and img_hsv_copy.shape[2] == 3 ):
    output = hsv2rgb(img_hsv_copy)
  else:
    output = img_hsv_copy
  output_uint8 = img_as_ubyte(output)
  imsave('' + outputPath, output_uint8)
  output_uint8


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='applies adaptative binarization and saves output.')
    parser.add_argument('-i', '--input_path', dest="input_path", type=str, required=True, help="image path")
    parser.add_argument('-o', '--output_path', dest="output_path", type=str, required=True, help="output path")
    args = parser.parse_args()
# if not os.path.exists(args.input_path):
#     raise IOError('input file does not exit')
output = reconstruction(args.input_path, args.output_path)
