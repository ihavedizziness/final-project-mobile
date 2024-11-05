package com.example.final_project_mobile.collection.domain.paging

data class PagedData<ITEM>(
    val items: List<ITEM>,
    val page: Page
)

data class Page(
    val total: Int,
    val totalPages: Int,
)