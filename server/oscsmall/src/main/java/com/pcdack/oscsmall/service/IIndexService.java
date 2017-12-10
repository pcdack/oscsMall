package com.pcdack.oscsmall.service;

import com.pcdack.oscsmall.common.ServerResponse;

public interface IIndexService {
    ServerResponse getIndex(int pageNum);
}