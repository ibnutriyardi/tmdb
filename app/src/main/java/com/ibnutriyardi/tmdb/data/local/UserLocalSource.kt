package com.ibnutriyardi.tmdb.data.local

import com.ibnutriyardi.tmdb.util.Constant
import com.ibnutriyardi.tmdb.util.LocalCache

class UserLocalSource {
    var token by LocalCache(Constant.UserKey.TOKEN, "")
}