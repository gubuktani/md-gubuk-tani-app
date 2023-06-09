package com.futureengineerdev.gubugtani.response

import com.google.gson.annotations.SerializedName
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

data class UpdateResponse(

	@field:SerializedName("result")
	val result: UpdateResult,

	@field:SerializedName("meta")
	val meta: UpdateMeta
)

data class UpdateMeta(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class UpdateUser(
	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,
)

data class UpdateResult(

	@field:SerializedName("user")
	val user: UpdateUser
)
