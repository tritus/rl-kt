package stick.display

import stick.geometry.Size

interface Display

expect class DisplayImpl(size: Size<Int>): Display