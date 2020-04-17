package com.wstik.kinde.data.models

import com.wstik.kinde.data.enums.FormError

interface FormState {
    fun isValid() : Boolean
    fun firstError() : FormError?
}