package com.remember.search;

import com.remember.search.vo.SearchRequest;
import com.remember.search.vo.SearchResponse;

/**
  * @author remember
  * @date 2020/4/29 16:41
  */
public interface ISearch {
    SearchResponse fetchAds(SearchRequest request);
}
