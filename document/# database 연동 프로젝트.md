# database 연동 프로젝트

## database의 구분 
- Master Table 의 구분
- Master Table, Work Table 의 구분
- `Master Data` : 어플리케이션을 사용하는 초기에 대량의 데이터를 추가하고, 운영하는 과정에서는 주로 조회 (SELECT)를 수행하는 데이터
- `Work Data` : 어플리케이션을 계속 운영하는 과정에서 수시로 데이터의 INSERT가 이루어지는 데이터

- Master Data를 보관하는 Table 을 Master Data라고 한다. 
- Work Data 를 보관하는 Table 을 Work Table 또는 Slave Table 이라고 한다.

## Entity와 Relation의 구분
- 주로 Master Data에 해당하는 부분을 entity라고 한다.
- work data에 해당하는 부분을 주로 relation이라고 한다.
- Work Data는 Master Data인 Entity와 항상 연동되는 데이터이다. 