package com.bereal.files.domain.interactors

import com.bereal.files.domain.model.User
import com.bereal.files.repository.UserRepository
import javax.inject.Inject

class GetUserInteractor @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(): User =
        userRepository.getCurrentUser()
}