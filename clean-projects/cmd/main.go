package main

import (
	"fmt"
	"gopkg.in/alecthomas/kingpin.v2"
	"io/fs"
	"os"
	"path/filepath"
)

var (
	cmd  = kingpin.New("clean-projects", "Clean projects like java, node")
	path = cmd.Flag("path", "root path to clean.").
		Default(".").String()
)

func main() {
	cmd.Version("0.0.1")
	cmd.HelpFlag.Short('h')
	command, err := cmd.Parse(os.Args[1:])
	fmt.Println(command)
	if err != nil {
		fmt.Println(err)
	}
	scanAndClean(*path)

}

func scanAndClean(path string) {
	var err error
	err = filepath.WalkDir(path, func(path string, d fs.DirEntry, err error) error {
		// java project
		if d.IsDir() && "target" == filepath.Base(path) {
			// if pom.xml exists
			_, err = os.Stat(filepath.Join(filepath.Dir(path), "pom.xml"))
			if nil == err {
				// exists
				fmt.Println("deleting ", path)
				err := os.RemoveAll(path)
				if err != nil {
					return err
				}
			}
		}
		// nodejs project
		if d.IsDir() && "node_modules" == filepath.Base(path) {
			// package.json exists
			_, err = os.Stat(filepath.Join(filepath.Dir(path), "package.json"))
			if nil == err {
				// exists
				fmt.Println("deleting ", path)
				err := os.RemoveAll(path)
				if err != nil {
					return err
				}
			}
		}
		return err
	})
	if err != nil {
		return
	}
}
