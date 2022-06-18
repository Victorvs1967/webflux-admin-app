package com.vvs.webfluxadminapp.model;

import org.bson.types.Binary;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Image {
  private String title;
  private Binary image;
}
