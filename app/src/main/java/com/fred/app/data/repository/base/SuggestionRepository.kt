package com.fred.app.data.repository.base

import com.fred.app.data.repository.model.Suggestion
import com.fred.app.util.State
import kotlinx.coroutines.flow.Flow

interface SuggestionRepository {
  suspend fun get(
      prompt: String,
  ): Flow<State<List<Suggestion>>>
}
