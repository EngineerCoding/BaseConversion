# BaseConversion
This repository is more of a test of my skills rather then something practical to use. This shows of my understanding of conversion to hexadecimal or other bases, however this algorithm does not work with all bases. This only will work when base is based on a binary logic, such as hexadecimal. Every four bits represents a character, and thus has 16 different characters (2 ^ 4 = 16). Bases like base10 or base9 do not rely on the binary and thus is not compatible with the main algorithm, however, one can easily override the encode and decode code to achieve this. 

To elaborate more on this system, let's take hexadecimal once more. For starters, we are a first going to represent a base10 number, convert it to binary and then convert it finally to hexadecimal.

So the as decimal number we take ```42```, no particular reason to pick that number. In binary we can represent ```42``` like so ```101010``` (```2^5 + 2^1```). Now we have to convert this binary data to hexadecimal, but like I said before hexadecimal has 4-bit characters, so we have to make a our binary number to 8 bits: ```0010 1010```. Each nibble (4 bits) represents 1 character, take a look at the conversion table:

| Base 10 number | Hexadecimal character |
| -------------- | --------------------- |
| 0 | 0 |
| 1 | 1 |
| 2 | 2 |
| 3 | 3 |
| 4 | 4 |
| 5 | 5 |
| 6 | 6 |
| 7 | 7 |
| 8 | 8 |
| 9 | 9 |
| 10 | A |
| 11 | B |
| 12 | C |
| 13 | D |
| 14 | E |
| 15 | F |

Now if we look at our nibbles again, we convert them to our base10 decimal and then we can convert them to a hexadecimal number, like so:

| Base  |  Nibble 1  | Nibble 2   |
| ----- | ---------- | ---------- |
| Binary | 0010 | 1010 |
| Decimal| 2 | 10 |
| Hexadecimal| 2 | A |

Now we know the hexadecimal representation of each nibble, we can say that base10 number ```42``` is in hexadecimal (base16) ```0x2A```. This ```0x``` is just for visualization, it is a prefix to make clear that it is hexadecimal.

Now if we want to do this with code, we can create a ```Base``` object via the following code:
```
final Base base16 = BaseFactory.create(16, "0123456789ABCDEF".toCharArray(), "0x")
```
This just creates the object without saving it in memory, when you want to save it into memory you can simply call ```BaseFactory.getBase(16, "0123456789ABCDEF".toCharArray(), "0x")``` which will try to pull it from the memory or create it when it is not loaded yet. When it is loaded (which you can check with ```BaseFactory.gotBase(16)```) you can simply get it by calling ```BaseFactory.getBase(16)```.

Note that the character array is in order of the hexadecimal value, when you reverse that order, the hexadecimal number will be reversed as well; those characters doesn't matter for the algorithm as long as the array is the same size as the base.

For further information on the classes, please refer to the source code where the documentation is.
