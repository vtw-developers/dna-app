# DnA Application
### API, 동기화, Agent 등 모든 연계를 담당하는 Flow를 실행하는 주체

### 코딩 규칙
1. Camel Route, Processor는 각각 route, processor 패키지에서 구현
2. Flow(Camel Route), Processor ID는 대문자로 시작하는 CamelCase로 작성
3. Camel Route, Processor 클래스에서 로직을 구현하지 말고, Camel가 관련없는 개별 클래스를 생성하여 구현
