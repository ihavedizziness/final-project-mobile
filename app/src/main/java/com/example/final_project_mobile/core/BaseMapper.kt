package com.example.final_project_mobile.core

fun interface BaseMapper<FROM, TO> {
    fun map(source: FROM): TO
}