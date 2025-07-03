package com.example.briefly.domain.usecase

import com.example.briefly.core.NoInputSuspendingUseCase
import com.example.briefly.core.Result

interface RefreshNewsListUseCase : NoInputSuspendingUseCase<Result<Unit>>