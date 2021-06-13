package lectures.myTeststttssss

object tests  extends App {


  val permanentlyClosedPattern = "(?i)^(?!.*\\b(?:permanently closed|seasonally closed|temporarily closed)\\b)(.*[^\\-,\\/\\s(*':=!\\[.|]).*\\b(?:closed)\\b.*".r
  val permanentlyClosedPattern(a) = "baldwin granite (closed!!!)"
  println(a)
  val permanentlyClosedPattern(b) = "Leona's Restaurants-Closed"
  println(b)
  val permanentlyClosedPattern(c) = "Learning Palace-Closed"
  println(c)
  val permanentlyClosedPattern(d) = "USA Bus Charter | CLOSED"
  println(d)
  val permanentlyClosedPattern(e) = "Invitation Homes Nashville-Closed"
  println(e)
  val permanentlyClosedPattern(f) = "treasures over time....closed"
  println(f)
  val permanentlyClosedPattern(g) = "holly hill used appliances ( temporarily closed)"
  println(g)
  val permanentlyClosedPattern(h) = "unify financial credit union (temporarily closed - restricted access)"
  println(h)





  /*
  val permanentlyClosedPattern = "(?i)(.*[^\\-,\\/\\s\\(\\*\\:\\=\\!\\[]).*(?=seasonally closed).*".r
  val permanentlyClosedPattern(a) = "h&r block - seasonally closed"
  println(a)
  */






  /*
  val permanentlyClosedPattern = "(?i)(.*[^\\-,\\/\\s\\(\\*\\:\\=\\!\\[]).*(?=temporarily closed).*".r
  val permanentlyClosedPattern(a) = "everything but water (temporarily closed)"
  println(a)
  val permanentlyClosedPattern(b) = "holly hill used appliances ( temporarily closed)"
  println(b)
  val permanentlyClosedPattern(c) = "unify financial credit union (temporarily closed - restricted access)"
  println(c)
  val permanentlyClosedPattern(d) = "zilker zephyr miniature train (temporarily closed for repair)"
  println(d)
  val permanentlyClosedPattern(e) = "bar bullfrog [temporarily closed]"
  println(e)
  val permanentlyClosedPattern(f) = "bar bullfrog comma ,temporarily closed"
  println(f)
  val permanentlyClosedPattern(g) = "bar bullfrog comma,temporarily closed"
  println(g)
  val permanentlyClosedPattern(h) = "bar bullfrog comma, temporarily closed"
  println(h)
  val permanentlyClosedPattern(i) = "mo's house of axe * temporarily closed *"
  println(i)
  */



  /*
  val permanentlyClosedPattern = "(?i)(.*[^\\-,\\/\\s\\(\\*\\:\\=\\|\\.]).*(?=permanently closed).*".r
  val permanentlyClosedPattern(a) = "baldwin granite (permanently closed!!!)"
  println(a)
  val permanentlyClosedPattern(b) = "sindee's ape vapes= permanently closed"
  println(b)
  val permanentlyClosedPattern(c) = "catherines-permanently closed"
  println(c)
  val permanentlyClosedPattern(d) = "catherines - permanently closed"
  println(d)
  val permanentlyClosedPattern(e) = "catherines- permanently closed"
  println(e)
  val permanentlyClosedPattern(f) = "catherines -permanently closed"
  println(f)
  val permanentlyClosedPattern(g) = "the new iberia bancorporationsouth branch is permanently closed. please visit our branch in broussard."
  println(g)
  val permanentlyClosedPattern(h) = "vineyard church of savannah ***permanently closed 12/2015***"
  println(h)
  val permanentlyClosedPattern(i) = "the yard @artists4israel (permanently closed)"
  println(i)
  val permanentlyClosedPattern(j) = "sweet shack 2.0 / permanently closed"
  println(j)
  val permanentlyClosedPattern(k) = "Churchill Mortgage PERMANENTLY CLOSED Benton"
  println(k)
  val permanentlyClosedPattern(l) = "luby's - (permanently closed)"
  println(l)
  val permanentlyClosedPattern(m) = "Fiorentino's -- permanently closed"
  */



}
