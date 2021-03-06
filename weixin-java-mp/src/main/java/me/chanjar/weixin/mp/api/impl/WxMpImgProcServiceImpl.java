package me.chanjar.weixin.mp.api.impl;

import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpImgProcService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.imgproc.WxMpImgProcAiCropResult;
import me.chanjar.weixin.mp.bean.imgproc.WxMpImgProcQrCodeResult;
import me.chanjar.weixin.mp.bean.imgproc.WxMpImgProcSuperResolutionResult;
import me.chanjar.weixin.mp.util.requestexecuter.ocr.OcrDiscernRequestExecutor;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static me.chanjar.weixin.mp.enums.WxMpApiUrl.ImgProc.AI_CROP;
import static me.chanjar.weixin.mp.enums.WxMpApiUrl.ImgProc.FILE_AI_CROP;
import static me.chanjar.weixin.mp.enums.WxMpApiUrl.ImgProc.FILE_QRCODE;
import static me.chanjar.weixin.mp.enums.WxMpApiUrl.ImgProc.FILE_SUPER_RESOLUTION;
import static me.chanjar.weixin.mp.enums.WxMpApiUrl.ImgProc.QRCODE;
import static me.chanjar.weixin.mp.enums.WxMpApiUrl.ImgProc.SUPER_RESOLUTION;

/**
 * 图像处理接口实现.
 * @author Theo Nie
 */
@RequiredArgsConstructor
public class WxMpImgProcServiceImpl implements WxMpImgProcService {
  private final WxMpService wxMpService;

  @Override
  public WxMpImgProcQrCodeResult qrCode(String imgUrl) throws WxErrorException {
    try {
      imgUrl = URLEncoder.encode(imgUrl, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      //ignore
    }

    final String result = this.wxMpService.get(String.format(QRCODE.getUrl(this.wxMpService.getWxMpConfigStorage()), imgUrl), null);
    return WxMpImgProcQrCodeResult.fromJson(result);
  }

  @Override
  public WxMpImgProcQrCodeResult qrCode(File imgFile) throws WxErrorException {
    String result = this.wxMpService.execute(OcrDiscernRequestExecutor.create(this.wxMpService.getRequestHttp()), FILE_QRCODE.getUrl(this.wxMpService.getWxMpConfigStorage()), imgFile);
    return WxMpImgProcQrCodeResult.fromJson(result);
  }

  @Override
  public WxMpImgProcSuperResolutionResult superResolution(String imgUrl) throws WxErrorException {
    try {
      imgUrl = URLEncoder.encode(imgUrl, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      //ignore
    }

    final String result = this.wxMpService.get(String.format(SUPER_RESOLUTION.getUrl(this.wxMpService.getWxMpConfigStorage()), imgUrl), null);
    return WxMpImgProcSuperResolutionResult.fromJson(result);
  }

  @Override
  public WxMpImgProcSuperResolutionResult superResolution(File imgFile) throws WxErrorException {
    String result = this.wxMpService.execute(OcrDiscernRequestExecutor.create(this.wxMpService.getRequestHttp()), FILE_SUPER_RESOLUTION.getUrl(this.wxMpService.getWxMpConfigStorage()), imgFile);
    return WxMpImgProcSuperResolutionResult.fromJson(result);
  }

  @Override
  public WxMpImgProcAiCropResult aiCrop(String imgUrl) throws WxErrorException {
    return this.aiCrop(imgUrl, "");
  }

  @Override
  public WxMpImgProcAiCropResult aiCrop(String imgUrl, String ratios) throws WxErrorException {
    try {
      imgUrl = URLEncoder.encode(imgUrl, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      //ignore
    }

    if (StringUtils.isEmpty(ratios)) {
      ratios = "";
    }

    final String result = this.wxMpService.get(String.format(AI_CROP.getUrl(this.wxMpService.getWxMpConfigStorage()), imgUrl, ratios), null);
    return WxMpImgProcAiCropResult.fromJson(result);
  }

  @Override
  public WxMpImgProcAiCropResult aiCrop(File imgFile) throws WxErrorException {
    return this.aiCrop(imgFile, "");
  }

  @Override
  public WxMpImgProcAiCropResult aiCrop(File imgFile, String ratios) throws WxErrorException {
    if (StringUtils.isEmpty(ratios)) {
      ratios = "";
    }

    String result = this.wxMpService.execute(OcrDiscernRequestExecutor.create(this.wxMpService.getRequestHttp()), String.format(FILE_AI_CROP.getUrl(this.wxMpService.getWxMpConfigStorage()), ratios), imgFile);
    return WxMpImgProcAiCropResult.fromJson(result);
  }
}
