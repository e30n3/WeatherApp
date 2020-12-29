package com.example.myapplication

import com.example.myapplication.model.AuthorizationChecker
import com.example.myapplication.model.IAuthorization
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun isCorrect() {
        val mockAuth: IAuthorization = mock()
        whenever(mockAuth.loginCheck("Andrey").thenReturn(true))
        whenever(mockAuth.passwordCheck("and1203").thenReturn(true))

        val managerChecker = AuthorizationChecker(mockAuth)
        managerChecker.check("Andrey","123456")
        verify(mockAuth).authorization("Andrey","123456")

    }

}