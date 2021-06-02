package com.homecookingshare.domain.recipe;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MakingTime implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long time;
}
