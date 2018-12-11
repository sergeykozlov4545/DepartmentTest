package com.example.sergey.departmenttest.extansion

fun IntRange.between(other: IntRange) = other.first <= first && last <= other.last