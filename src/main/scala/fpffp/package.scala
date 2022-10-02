package fpffp

type Id[A] = A

type Compose[F[_], G[_]] = [X] =>> F[G[X]]
