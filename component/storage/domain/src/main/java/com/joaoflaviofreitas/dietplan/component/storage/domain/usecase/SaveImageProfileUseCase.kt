package com.joaoflaviofreitas.dietplan.component.storage.domain.usecase

import android.net.Uri
import com.joaoflaviofreitas.dietplan.component.storage.domain.DataState

interface SaveImageProfileUseCase {
    suspend fun execute(uri: Uri?): DataState<Uri?>
}
