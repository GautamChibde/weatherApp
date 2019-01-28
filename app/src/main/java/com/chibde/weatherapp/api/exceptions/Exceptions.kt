package com.chibde.weatherapp.api.exceptions

import java.io.IOException

class NoConnectivityException : IOException()
class BadRequestException(override var message: String) : IOException()
class InternalServerException(override var message: String) : IOException()
