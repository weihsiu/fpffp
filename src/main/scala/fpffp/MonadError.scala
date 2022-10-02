package fpffp

trait MonadError[F[_], E] extends ApplicativeError[F, E] with Monad[F]

object MonadError:
  def apply[F[_], E](using ME: MonadError[F, E]): MonadError[F, E] = ME

  given [F[_], E](using
      AE: ApplicativeError[F, E],
      M: Monad[F]
  ): MonadError[F, E] with
    export AE.*
    export M._flatMap
