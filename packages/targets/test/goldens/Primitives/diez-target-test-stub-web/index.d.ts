// Stub core type declaration file for web.

export declare class ChildComponent {
  purr(): void;
}

export declare class EmptyComponent {
}

export declare class Primitives extends StateBag {
  number: number;
  integer: number;
  float: number;
  string: string;
  boolean: boolean;
  integers: number[][];
  strings: string[][][];
  child: ChildComponent;
  childs: ChildComponent[][];
  emptyChild: EmptyComponent;
}

