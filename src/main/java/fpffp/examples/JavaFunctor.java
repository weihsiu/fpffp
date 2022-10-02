package fpffp.examples;

import java.util.*;
import java.util.stream.Collectors;

class JavaFunctor {
  public static void main(String[] args) {
    List<Integer> ns = Arrays.asList(1, 2, 3, 4, 5);
    List<String> ss1 = new ArrayList<>();
    for (Integer n : ns)
      ss1.add("a".repeat(n));
    List<String> ss2 = ns.stream().map(n -> "a".repeat(n)).collect(Collectors.toList());

    Optional<Integer> oi1 = Optional.of(123);
    Optional<String> os1 = oi1.map(x -> x.toString());
    Optional<Integer> oi2 = Optional.empty();
    Optional<String> os2 = oi2.map(x -> x.toString());

    List<Integer> ns2 = Arrays.asList(-6, 4, 1, -2, 3, -5);
    List<Optional<Integer>> ons = ns2.stream().map(n -> {
      Optional<Integer> on = n < 0 ? Optional.empty() : Optional.of(n);
      return on;
    }).collect(Collectors.toList());
    List<Optional<String>> oss = ons.stream().map(on ->
      on.map(n -> "a".repeat(n))
    ).collect(Collectors.toList());
  }
}
