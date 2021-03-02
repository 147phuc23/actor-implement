# actor-implement

Self implementation about actor model and basic knowledge about concurrency

## Kiến thức

### Concurrency

Để hiểu về concurrency, chúng ta cũng nên tìm hiểu qua về parallel một chút. Nhiều bạn nghĩ parallel chỉ đơn thuần là
threading, là multi-core?

Chúng ta có thể hiểu là concurrent là làm nhiều task cùng một lúc, nhưng chưa chắc các task đó đã diễn ra song song.

Việc sử dụng nhiều thread trong kiến trúc các máy tính mới các ngôn ngữ OOP gây ra vấn đề về resource sharing, data
consistancy.

Một ví dụ đơn giản, khi n thread chạy độc lập, mỗi thread đều tính toán chung meth (tất cả đều gọi `Object2.methodA()`
đồng thời), khi đó những biến dùng trong method, giá trị sẽ bị thay đổi trong khi không đưa ra khỏi hàm.

Việc này dẫn đến unpredicted output của `methodA`

![](https://doc.akka.io/docs/akka/current/typed/guide/diagrams/seq_chart_multi_thread.png)

Một cách giải quyết đơn giản là dùng bên ngoài methodA, hoặc những biến dùng chung (mutex lock, semaphore). Những cách
làm trên ở kiến trúc mới làm giảm hiệu suất của ứng dụng, tốn resource của cpu, hoặc tốn công suspend thread và restore
thread.

Những thread đang đợi tới lượt sẽ bị blocking và không thực hiện tính toán, gây lãng phí tài nguyên. Locking còn có thể
gây ra

#### The illusion of shared memory on modern computer architectures

Ngoài ra, với multicore processing, mỗi thread sẽ load biến dùng chung lên cache với mỗi core riêng biệt, do đó sẽ có
hiện tượng ở core 1 `a=3` và core2 `a=4`.

### Actor model

Actor model là một giải pháp để giải quyết vấn đề trên. Các object là các đối tượng độc lập, tương tác với nhau bằng
message passing. Message sẽ được đưa vào mailbox của mỗi o. Mỗi object sẽ chạy độc lập trên một thread, và xử lý message
trong mailbox

#### Actor

- message passing - các actor trao đổi với nhau bằng cách gửi message vào mailbox của nhau. Bạn muốn bảo một actor làm
  gì cho bạn: gửi message cho nó. Bạn muốn giết một actor: gửi message cho nó. Bạn muốn truy cập thông tin một actor:
  gửi message cho nó rồi check mailbox.

- never share memory - mỗi actor có một state riêng mà không có actor nào có thể truy cập hay thay đổi được.

- there are many actors - Actor là thứ sống theo bầy đàn. Trong mô hình này, tất cả đều là actor hoặc không có một actor
  nào cả. Đồng thời actor được định danh (giống như bạn được cha mẹ bạn cấp cho cái tên), tui sẽ nói rõ hơn về cái này
  trong phần tiếp theo.

- asynchronous - mọi message đều là bất đồng bộ, tức là lúc bạn bấm gửi và lúc nào nó tới là hai chuyện khác nhau.

## Code implementation

### Những chỗ lưu ý khi hiện thực

### Một số vấn đề chưa giải quyết
- Xử lý lỗi
- Kiểu trả về của mỗi đối 
## Tham khảo các thư viện khác
Một số cách tiếp cận khác như của NodeJS hoặc VertX event loop
### Event loop
Ứng dụng sẽ chạy trên 1 thread chính là event loop.
Mỗi event loop sẽ có một event queue và callstack.

Mở đầu, EL sẽ chạy tuần tự cho tới khi stack được trống. Khi có 1 sự kiện bất đồng bộ xảy ra (IO, User event), một callback / eventHandling tương ứng sẽ được đẩy vào callstack.

Sẽ có một thread pool để khi gặp những tác vụ nặng, IO, những tác vụ này sẽ được đẩy xuống slaves để giải quyết. Khi giải quyết xong, các thread con sẽ gởi thông báo tới thread chính qua callback và đẩy callback vào event queue.
### Vertx
Là một mô hình reactive có sử dụng event loop. Mỗi ứng dụng sẽ gồm nhiều verticle, mà vertical cơ bản sẽ có một event loop.
## Reference
https://kipalog.com/posts/Elixir-Erlang--Actor-model-va-Concurrency
https://doc.akka.io/docs/akka/current/typed/guide/actors-intro.html